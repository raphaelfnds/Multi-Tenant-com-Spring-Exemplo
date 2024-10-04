INSERT INTO public.client (id, final_contract, number_contract, situation, start_contract, cpf_cnpj, client_name)
VALUES (1, '2050-12-01', '1', true, '2024-09-02', '99123123000199', 'EMPRESA LTDA');


INSERT INTO public.client_user
(id, acess_type, cpf, email, user_name, nick_name, "password", situation, client_id)
VALUES(1, 'MASTER', '12345698799', 'email@email.com', 'Raphael Fernandes', 'raphaelfnds', '$2a$10$wR0VQBC6DRgjMj0WOha/7OHFF5bkO3tZPrBhEJWF88MLI1E1GJLzG', true, 1);

-- A primeira insersão de Client deve ter erro, tentar novamente pois o hibernete recalcula o ultimo Id e corrige

