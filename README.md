# MicroStream GigaMap (beta)

Welcome to the Beta program for our new GigaMap.

An indexed collection designed to cope with vast amounts of data.

It stores the data in nested, lazy-loaded segments backed by indices.
This allows for efficient querying of data without the need to load all of it into memory.
Instead, only the segments required to return the resulting entities are loaded on demand.
With this approach, GigaMap can handle billions of entities with exceptional performance.

Compared to other collections, the main advantage of GigaMap is its ability to query data
without the need to load all the data first. This makes it a highly efficient and flexible
solution for managing, querying, and storing large quantities of data.

The indices and queries are created with a Java API, so learning another query language is unnecessary.

## Features



## Setup

To partake in the Beta, just create an account at ...
There you will find the Maven coordinates for the library.

Add this dependency to your project, and you are good to go:

```xml
<groupId>one.microstream</groupId>
<artifactId>gigamap</artifactId>
<version>1.0.0-beta1</version>
```


## Examples
