# RSS Feed Source

Simple Java application to fetch & output RSS feeds.

**Main Java dependencies**
- ROME
- Spring Cloud

## Build

```
# java
./mvn install

# docker
cd target && docker build -t data-source-rss .
```

## Run

**Requirements**
- RabbitMQ message broker
- Redis

```
java -jar target/data-source-rss.jar

# or

docker run data-source-rss
```

Send a sample

```json
{
  "url": "https://feeds.feedburner.com/les-crises-fr"
}
```

## Configuration

Configuration can be done via classic Spring BOOT configuration.

See [application.yml](src/main/resources/application.yml) for an example

## Input Message

```json
{
  "url": "https://feeds.com"
}
```

## Output Message
[DublinCoreModule](src/main/java/fr/asso/placeholder/data/sources/rss/models/DublinCoreModule.java)
```json
{
  "uri": "entry url",
  "link": "entry uri",
  "title": "title",
  "publishedDate": "2019-12-08T05:30:27.000+0000",
  "description": {
    "type": "html",
    "value": "<p>This is a nice entry</p>"
  },
  "contents": [{ "type": "html", "value": "<p>Some content</p>" }, ... ],
  "authors": [{ "type": "text", "value": "Jean Marie" }],
  "contributors": [{ "type": "text", "value": "Jean Marie" }],
  "source": "https://feeds.url/",
  "modules": [{ }],
  "enclosures": [{ "url": "http://image.jpg", "type": "jpg", "length": "2134354"}],
  "categories": [{ "name": "Category 1", "taxonomyUri": ""}],
  "updatedDate": "2019-12-08T05:30:27.000+0000"
}
```
