# Rodando a aplicação
### docker
é possível iniciar a aplicação de maneira rápida utilizando o plugin do Spring para gerar uma imagem docker, para isso:
-  rodar o comando `mvn spring-boot:build-image`
  
Após a execução do comando acima uma imagem ficara disponível no seu computador, para rodar a imagem em um container:
- executar o comando`docker run -p 8080:8080 desafio:0.0.1-SNAPSHOT`

Com a execução dos passos acima, a aplicação estará disponível na porta localhost:8080
# Decisões técnicas
### Chain of Responsibility
Foi o método escolhido para tratar uma requisição de batida de ponto, uma vez que a manutenibilidade foi uma preocupação ao desenvolver essa aplicação, visto que ela é relativamente simples e poderia ser escalada.
### pontos positivos
- Poder tratar isoladamente cada aspecto da requisição.
- é possível entender o código com mais facilidade.
- Utiliza princípios da orientação a objetos e SOLID.
### ressalvas
Entendo que a ordem dos Handlers seja crucial e que a alteração na ordem pode interferir no funcionamento do programa, mas acredito que o mesmo também ocorreria com uma abordagem mais procedural.
Como mencionei, esse foi o método escolhido para que seja possível demonstrar conceitos básicos da OO, como herança e abstração.
### Regras de negócio
As regras de negócio foram deixadas na camada de domínio da aplicação, dessa forma, quando houverem mudanças no código as regras permanecerão as mesmas, aumentando a escalabilidade do projeto.

