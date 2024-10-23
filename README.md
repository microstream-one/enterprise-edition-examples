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



      



## Setup

To partake in the Beta, just create a free account at https://microstream.cloud, there you will find the Maven repository for the library.

Then add this dependency to your project, and you are good to go:

```xml
<groupId>one.microstream</groupId>
<artifactId>gigamap</artifactId>
<version>1.0.0-beta1</version>
```


## Get Started

The complete code can be found in the [examples](/examples) folder.

First, we need an entity that we will store in the GigaMap.

```java
public class Person
{
	private final UUID      id         = UUID.randomUUID();
	private       String    firstName  ;
	private       String    lastName   ;
	private       LocalDate dateOfBirth;
	private       Address   address    ;
	
	// ...
}

public class Address
{
	private String street;
	private String city;
	private String country;

	// ...
}
```

Next, we create the indexers, which are used for the GigaMap. Since we need them later on to create queries, we define them as constants in a dedicated type.

For most use cases, the predefined abstract indexers will be sufficient. They cover the most common types which are used in indices.

The sole purpose of these indexers is to extract the entity's value, which will be stored in the index.

```java
public class PersonIndices
{
	public final static Indexer.AbstractUUID<Person> id = new Indexer.AbstractUUID<>()
	{
		@Override
		protected UUID getUUID(final Person entity)
		{
			return entity.getId();
		}
	};

	public final static Indexer.AbstractString<Person> firstName = new Indexer.AbstractString<>()
	{
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getFirstName();
		}
	};
	
	public final static Indexer.AbstractString<Person> lastName = new Indexer.AbstractString<>()
	{
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getLastName();
		}
	};
	
	public final static Indexer.AbstractLocalDate<Person> dateOfBirth = new Indexer.AbstractLocalDate<>()
	{
		@Override
		protected LocalDate getDate(final Person entity)
		{
			return entity.getDateOfBirth();
		}
	};

	public final static Indexer.AbstractString<Person> city = new Indexer.AbstractString<>()
	{
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getAddress().getCity();
		}
	};

	public final static Indexer.AbstractString<Person> country = new Indexer.AbstractString<>()
	{
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getAddress().getCountry();
		}
	};
}
```

Now, we can create the GigaMap itself.

```java
final GigaMap<Person> gigaMap = GigaMap.<Person>Builder()
	.withBitmapIdentityIndex(PersonIndices.id)
	.withBitmapIndex(PersonIndices.firstName)
	.withBitmapIndex(PersonIndices.lastName)
	.withBitmapIndex(PersonIndices.dateOfBirth)
	.withBitmapIndex(PersonIndices.city)
	.withBitmapIndex(PersonIndices.country)
	.build();
```

After adding data to the GigaMap, let's try some queries.

```java
// Get all Johns
List<Person> result = gigaMap.query(PersonIndices.firstName.is("John")).toList();
```

```java
// Geta all born in the year 2000
List<Person> result = gigaMap.query(PersonIndices.dateOfBirth.isYear(2000)).toList();
```

Have a look at the methods of GigaQuery. There is not only `toList()` but many more data retrieval methods. You can iterate, stream or page through the results.
