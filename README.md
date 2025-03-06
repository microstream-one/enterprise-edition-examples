# MicroStream Enterprise Edition Examples

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

## Demos

- There is a version of the [BookStore Demo](https://github.com/eclipse-store/bookstore-demo/tree/gigamap) which uses the GigaMap
- More demos are on the way

## Documentation

Online manual is available at https://docs.microstream.one/enterprise
