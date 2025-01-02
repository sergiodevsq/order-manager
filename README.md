# GERENCIAMENTO DE PEDIDOS

    ## DESCRIÇÃO
        O objetivo do projeto é expor recursos para recebimento de pedidos(orders) para outros serviços externos, através de end-points dos recursos.
        Existem dois recursos:
        - product: Visa cadastrar produtos que serão utilizados nos pedidos, onde um pedido pode ter diversos produtos, mas os mesmos só podem
        ter produtos previamente cadastrados. É permitido buscar todos os produtos ou por ID específico, Alterar um produto ou deletar.

        - order: Recurso para receber pedidos vindos de serviços externos. Podemos listar todos ou um ID específico, alterar algumas informações e deletar.

        OBS: Somente são permitidos pedidos de produtos cadastrados.

    ## TECNOLOGIAS UTILIZADAS
        Java 17 com Spring Boot 3.4.1
        RabbitMQ 4 
        Postgresql 13
        Docker e Docker Compose

    ## FUNCIONAMENTO DO PROJETO
        O serviço order-v2 possui os recursos e end-points para cadastrar products e orders e o serviço está dockerizado.
        Todos os dados são persistidos no banco em postgresql, que está configurado em container utilizando o banco chamado order_db.
        Utiliza o RabbitMQ como broker de filas para melhorar o desenpenho nas inclusões das orders, evitando que o banco de dados 
        se trasforme em um gargalo no momento da inclusão.
        O serviço sub-order faz a persistência das orders fazendo a leitura da fila no RabbitMQ.

    ## EXECUTANDO O PROJETO
        O projeto esta dockerizado, sendo de fácil execução. Após realizar o clone do projeto, será criado o diretório order-manager que contém os diretórios docker-compose com o arquivo docker-compose.yaml contendo o script para realizar o build dos serviços order-v2 e sub-order, além de subir os containers do postgresql, criando o banco de dados order_db, se for a primeira execução do container.
        Também será executado o container com uma instância do RabbitMQ para a fila de inclusão de orders.

        Procedimento para plataforma Linux.

        1 - Baixe o zip do projeto em: https://github.com/sergiodevsq/order-manager
        2 - Após descompactar, entre no diretorio order-manager-main/docker-compose
        3 - Certifique-se de ter o Docker e Docker-compose instalados, então execute:
            $> docker-compose up -d --build
        4 - Se tudo der certo, teremos um container para order-v2, outro para sub-order e mais dois containers para o postgresql e o RabbitMQ

    ## CADASTRO DE PRODUTOS

        - POST em http://localhost:8080/product/create
            {
                "name": "Smartphone Samsung J7",
                "description": "Smartphone Samsung com tela super amoled e processador octacore",
                "value": 1779.90
            }

    ## LISTANDO OS PRODUTOS CADASTRADOS
        
        #Lista por um id específico
        - GET em http://localhost:8080/product/{id}

        #Lista todos os produtos cadastrados
        - GET em http://localhost:8080/product/all

        #Lista os produtos com paginação
        - GET em http://localhost:8080/product/page
        
        Podemos passar alguns query params para customizar a paginação, se não passar assume valor padrão:
        - GET em http://localhost:8080/product/page?page=valor&linesPerPage=valor&sortBy=valor&ascending=valor
            page: padrão 0,  linesPerPage: padrão 24, sortBy: padrão campo name,  ascending: padrão true

    ## DELETANDO UM PRODUTO
        - DELETE em  http://localhost:8080/product/delelte/{id}   

    ## ATUALIZANDO UM PRODUTO
        - PUT em http://localhost:8080/product/update/{id}
            {
                "name": "Espremedor laranja Eletrolux",
                "description": "Espremedor de laranjas Eletrolux",
                "value": 199.90
            }


    ## INCLUINDO PEDIDOS (ORDER)

        - POST  em  http://localhost:8080/order/create
               {
                "client": "cliente 1",
                "products": [
                        {
                            "id": 1,
                            "name": "Espremedor laranja Eletrolux",
                            "description": "Espremedor de laranjas Eletrolux",
                            "value": 199.90,
                            "quantity": 4
                        },
                        {
                            "id": 2,
                            "name": "Espremedor laranja Eletrolux",
                            "description": "Espremedor de laranjas Eletrolux",
                            "value": 199.90,
                            "quantity": 4
                        }
                    ]
                } 
            OBS: Existe validação para verificar se os produtos estão cadastrados.

    
    ## LISTANDO OS PEDIDOS INCLUIDOS 
      
        #Lista por um id específico
        - GET em http://localhost:8080/order/{id}

        #Lista todos os produtos cadastrados
        - GET em http://localhost:8080/order/all

        #Lista os produtos com paginação
        - GET em http://localhost:8080/order/page
        
        Podemos passar alguns query params para customizar a paginação, se não passar assume valor padrão:
        - GET em http://localhost:8080/order/page?page=valor&linesPerPage=valor&sortBy=valor&ascending=valor
            page: padrão 0,  linesPerPage: padrão 24, sortBy: padrão campo name,  ascending: padrão true

    ## DELETANDO UM PRODUTO
        - DELETE em  http://localhost:8080/order/delelte/{id}   

    ## ATUALIZANDO UM PRODUTO
        - PUT em http://localhost:8080/order/update/{id}
            {
            "client": "cliente 1",
            "products": [
                    {
                        "id": 1,
                        "name": "Espremedor laranja Eletrolux",
                        "description": "Espremedor de laranjas Eletrolux",
                        "value": 199.90,
                        "quantity": 4
                    }
                ]
            }