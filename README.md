Instalar a IDE Spring Tools Suite --> https://spring.io/tools (ou outra IDE com extensão para Spring)

Extrair o conteúdo do arquivo .zip

Importar o arquivo na IDE --> File > Import > Existing Projects into Worspace > selecionar arquivo > finish

Instalar MySQL WorkBench --> https://www.mysql.com/downloads/ (ou outra IDE que rode a linguagem SQL para banco de dados)

No MySQL, criar um banco de dados chamado "iflearn" --> executar o comando "create database iflearn;" > executar em seguida o comando "use iflearn;"

No Spring Tools, alterar o username do seu banco de dados no "aplication.properties" na rota scr/main/resources --> spring.datasource.username=seuusername

No Spring Tools, alterar a password do seu banco de dados no "aplication.properties" na rota scr/main/resources --> spring.datasource.password=suapassword

Rodar a classe "IFLearnMain.java" na rota src/main/java/iflearn --> botão direito > run as > java aplication
