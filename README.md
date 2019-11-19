# log4j2-converters-plugin
log4j2 converters plugin includes
* CRLF Converter to sanitize CRLF injections in logs. Refer [LogCrlfConverter.java](./src/main/java/com/sp/plugin/log4j2/LogCrlfConverter.java)
* Masking Converter to mask sensitive data in logs. Refer [LogMaskingConverter.java](./src/main/java/com/sp/plugin/log4j2/LogMaskingConverter.java)

### How to use Masking-Converter?
- Build this repository.
- Add this plugin as a dependency in your project.
- Mention key as **%spi** in your PatternLayout.
```
'[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1}- %spi%n'
```
### How to use CRLF-Converter?
- Build this repository.
- Add this plugin as a dependency in your project.
- Mention key as **%crlf** in your PatternLayout.
```
'[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1}- %crlf%n'
```

### Maybe you want to use both CRLF and Masking Converter, So How to use CRLF-Converter and Masking-Converter at the same time?
- Build this repository.
- Add this plugin as a dependency in your project.
- Mention key as **%crlf{%spi}** in your PatternLayout. Here we are passing **%spi** as a option to **%crlf** key
```
'[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1}- %crlf{%spi}%n'
```
