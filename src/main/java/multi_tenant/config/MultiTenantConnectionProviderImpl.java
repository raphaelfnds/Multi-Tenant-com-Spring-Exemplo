package multi_tenant.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

	private static final long serialVersionUID = 1L;

	private final Map<String, DataSource> dataSources = new HashMap<>();
	private final Map<String, String> tenantUrls = new HashMap<>();
	private final Map<String, String> clientNameToTenantIdMap = new HashMap<>();

	private final Environment environment;

	@Autowired
	public MultiTenantConnectionProviderImpl(Environment environment) {
		this.environment = environment;

		String[] tenants = environment.getProperty("tenants", "").split(",");
		for (String tenantId : tenants) {
			tenantId = tenantId.trim();
			String url = environment.getProperty("tenant." + tenantId + ".url");
			String username = environment.getProperty("tenant." + tenantId + ".username");
			String password = environment.getProperty("tenant." + tenantId + ".password");
			String clientName = environment.getProperty("tenant." + tenantId + ".clientName");

			DataSource dataSource = DataSourceBuilder.create().driverClassName("org.postgresql.Driver").url(url)
					.username(username).password(password).build();

			dataSources.put(tenantId, dataSource);
			tenantUrls.put(tenantId, url);

			if (clientName != null && !clientName.isEmpty()) {
				clientNameToTenantIdMap.put(clientName, tenantId);
			}
		}
	}

	public String getTenantIdForClientName(String clientName) {
		return clientNameToTenantIdMap.get(clientName);
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		// DataSource do users_app quando o tenantId não estiver definido
		DataSource dataSource = dataSources.get("users_app");
		if (dataSource != null) {
			return dataSource.getConnection();
		} else {
			throw new SQLException("DataSource 'users_app' não encontrado");
		}
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		DataSource dataSource = dataSources.get(tenantIdentifier);
		if (dataSource != null) {
			String url = tenantUrls.get(tenantIdentifier);
			System.out.println("Obtendo conexão para o tenant '" + tenantIdentifier + "' usando o banco de dados: " + url);
			return dataSource.getConnection();
		} else {
			throw new SQLException("Não foi possível encontrar o DataSource para o cliente: " + tenantIdentifier);
		}
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(Class<?> unwrapType) {
		return MultiTenantConnectionProvider.class.equals(unwrapType)
				|| MultiTenantConnectionProviderImpl.class.isAssignableFrom(unwrapType);
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		if (isUnwrappableAs(unwrapType)) {
			return (T) this;
		} else {
			throw new IllegalArgumentException("Cannot unwrap to " + unwrapType);
		}
	}
}
