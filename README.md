# play-java-ebean-querybeans-example

A minimal Play Java application that uses Ebean's [query beans](https://ebean.io/docs/query/query-beans) with [Play Ebean](https://github.com/playframework/play-ebean).

Play Ebean doesn't support query beans itself yet ([playframework/play-ebean#61](https://github.com/playframework/play-ebean/issues/61)). This project shows the settings they need today, and their limitations.

Tested with Play 3.0.9, Play Ebean 8.5.0 (Ebean 17.1.0) and sbt 1.13.0, on JDK 11, 17, 21 and 25.

## Running

```bash
sbt test   # saves some buildings, and queries them with a query bean
sbt run    # http://localhost:9000 lists the buildings whose names start with "A" (none, as the database starts empty)
sbt stage  # packages the application
```

## The settings

All of them are in [`build.sbt`](build.sbt):

- **`querybean-generator`** is Ebean's annotation processor, which generates the query beans (here `models.query.QBuilding`) while javac compiles the entities. Use the version of the Ebean that your Play Ebean version uses: 17.1.0 for Play Ebean 8.5.0 and 7.5.0.
- **`-s`** writes the generated sources to `target/scala-2.13/src_managed/main` instead of the classes folder. That way, IDEs see them as generated sources, and the Ebean agent doesn't print `Expecting a .class file but got QBuilding.java ... ignoring`.
- **`-proc:full`**: since JDK 23, javac runs annotation processors only when asked to. Remove it if your JDK doesn't know this option.
- **`playEbeanModels`**: the Ebean agent rewrites how code uses the properties of query beans, like `new QBuilding().name`, so the code that uses query beans needs to be enhanced too, not only the entities. Add every package with such code, like `controllers.*` here. Without it, the properties are `null`, and `HomeController` fails with a `NullPointerException`.
- **`Compile / doc / sources`** and **`Compile / packageDoc / publishArtifact`** turn off the API docs of the application. `sbt stage` and `sbt dist` would otherwise run Scaladoc, which also reads the Java sources but doesn't see the query beans, and fails with `object query is not a member of package models`.

## Limitations

- Only Java code can use query beans. sbt compiles the Scala sources, like the Twirl templates, before javac generates the query beans, so Scala code using them fails with `object query is not a member of package models`. Use query beans in Java code, and pass the results to the templates.
- With sbt 1, do a clean build after changing entities. sbt can't tell which sources the generated query beans come from, and warns `Could not determine source for class models.query.QBuilding`. So an incremental build may not recompile code that uses an outdated query bean, which then fails at runtime with a `NoSuchMethodError`.

## License

To the extent possible under law, the author has dedicated all copyright and related and neighboring rights to this software to the public domain worldwide, see [CC0 1.0](LICENSE).
