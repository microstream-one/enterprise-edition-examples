# MicroStream Enterprise Edition (Beta)

Welcome to the Beta program for our new Enterprise Edition.

If you have suggestions or find any issues, please feel free to open an [issue](https://github.com/microstream-one/gigamap-beta/issues) or a [discussion](https://github.com/microstream-one/gigamap-beta/discussions).


## Description

MicroStream Enterprise Edition is a powerful extension to EclipseStore. The main features are indexing and a GigaMap providing fully automated lazy loading.

### Indexing

Indexing is a data structure technique to optimize search performance in EclipseStore, comparable to indexing concepts in database systems. It creates a sorted list of values and their corresponding objects, allowing EclipseStore to quickly locate specific objects without scanning the entire collection. Think of it like an index in a book: it points to the specific page where a particular topic is discussed, saving you the time of flipping through every page.

#### Off-Heap Bitmap Indexing: A Speed Demon

Bitmap indexes are a specialized data structure that uses bitmaps to represent the presence or absence of a specific value within a collection. Each bit in the bitmap corresponds to an element in the collection. If the value is present in the element, the bit is set to 1; otherwise, it’s 0. The MicroStream bitmap index is managed off-heap.

#### Why Off-Heap Management Makes it Lightning-Fast

- **Direct Memory Access**: By managing the bitmap index off-heap, the index data is stored directly in system memory, bypassing the Java heap. This eliminates the overhead of garbage collection, a major performance bottleneck in traditional Java applications.
- **Reduced GC Pressure**: Off-heap storage reduces the memory footprint of the Java heap, leading to fewer garbage collection cycles and improved overall performance.
- **Optimized Read/Write Operations**: Direct memory access allows for faster read and write operations on the bitmap index, further accelerating search queries.

### GigaMap

GigaMap is a specialized collection designed to optimize performance and memory usage in EclipseStore. It leverages off-heap bitmap indexing to enable lightning-fast searches and lazy loading of objects, without the need for manual reference management.

- **Off-Heap Bitmap Index**: GigaMap uses the off-heap bitmap index concept to provide rapid lookup of objects based on specific criteria.
- **Automated Lazy-Loading**: When an object is accessed for the first time, GigaMap efficiently fetches and loads it from the underlying storage, minimizing memory footprint and improving startup time.
- **Transparent Integration**: GigaMap seamlessly integrates with EclipseStore’s data model, making it easy to use and maintain.
- **Scalability**: GigaMap can handle massive datasets, thanks to its efficient indexing and lazy-loading mechanisms.


## Setup

To partake in the Beta, just create a free account at https://microstream.cloud, there you will find the Maven repository for the library.

Then add this dependency to your project, and you are good to go:

```xml
<groupId>one.microstream</groupId>
<artifactId>gigamap</artifactId>
<version>1.0.0-beta1</version>
```

## Demos

- There is a version of the [BookStore Demo](https://github.com/eclipse-store/bookstore-demo/tree/gigamap) which uses the GigaMap
- More demos are on the way

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
