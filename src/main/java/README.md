#运行springboot的几种方式
###	1.直接在java文件中生成main方法，调用SpringApplication.run
###	2.创建app.groovy脚本，通过springboot cli运行  spring run app.groovy
###	3.通过maven运行
###		3.1.进入到项目根路径下，运行mvn spring-boot:run
###		3.2.将项目打包成Jar,通过java -jar 运行，这里需要特别说明一下，在pom.xml中没有指定package的类型，默认引用的是parent中的jar,所以打包时是生成的jar包
通过jar打包引用了spring-boot-maven-plugin插件
查看jar包内部文件：jar tvf target/myproject-0.0.1-SNAPSHOT.jar
