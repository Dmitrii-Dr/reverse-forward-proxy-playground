`# gateway-proxy-lib

This is a Java library (Maven) for sharing the @Gateway annotation between services.

## Usage
Add this library as a dependency to your Maven project:

```
<dependency>
    <groupId>com.dmdr</groupId>
    <artifactId>gateway-proxy-lib</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Provided Annotation
- `@Gateway(url = "...")` — Mark your resource classes for gateway registration.
