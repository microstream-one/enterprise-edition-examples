package one.microstream.gigamap.examples;

import java.nio.file.Paths;
import java.time.Year;
import java.util.List;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import com.github.javafaker.Faker;

import one.microstream.gigamap.GigaMap;
import one.microstream.gigamap.GigaQuery;

public class BasicExample
{
	final static EmbeddedStorageManager storageManager = startStorage();
	final static GigaMap<Person>        gigaMap        = ensureGigaMap();

	static EmbeddedStorageManager startStorage()
	{
		return EmbeddedStorage.start(Paths.get("target/basic-example"));
	}
	
	@SuppressWarnings("unchecked")
	static GigaMap<Person> ensureGigaMap()
	{
		GigaMap<Person> gigaMap = (GigaMap<Person>)storageManager.root();
		if(gigaMap == null)
		{
			System.out.print("Creating random data ... ");
			
			storageManager.setRoot(gigaMap = RandomGenerator.createMap(10_000));
			storageManager.storeRoot();
			
			System.out.println("finished");
			printSpacer();
		}
		return gigaMap;
	}
	
	
	public static void main(final String[] args)
	{
		queryExamples();
		
//		updateExample();
		
		storageManager.shutdown();
	}
	
	static void queryExamples()
	{
		queryExample1();
		printSpacer();
		queryExample2();
		printSpacer();
		queryExample3();
		printSpacer();
		queryExample4();
		printSpacer();
	}
	
	
	static void queryExample1()
	{
		query(
			"Thomases",
			gigaMap.query(PersonIndices.firstName.is("Thomas"))
		);
	}
	
	static void queryExample2()
	{
		final int birthYear = Year.now().getValue() - 25;
		query(
			"25 year olds",
			gigaMap.query(PersonIndices.dateOfBirth.isYear(birthYear))
		);
	}
	
	static void queryExample3()
	{
		query(
			"Germans and Austrians",
			gigaMap.query(PersonIndices.country.in("Germany", "Austria"))
		);
	}
	
	static void queryExample4()
	{
		query(
			"%sch% in last name",
			gigaMap.query(PersonIndices.lastName.contains("sch"))
		);
	}
	
	static void updateExample()
	{
		final Person person = gigaMap.get(0);
		System.out.println("Before: " + person);
		
		// updates person and indices
		gigaMap.update(person, p -> p.setAddress(new Address(new Faker())));
		gigaMap.store();
		
		System.out.println("After: " + person);
	}
	
	
	static void query(
		final String title,
		final GigaQuery<Person> query
	)
	{
		System.out.println(title);
		final long start = System.currentTimeMillis();
		
		final List<Person> result = query.toList(10);
		System.out.println((System.currentTimeMillis() - start) + " ms");
		
		result.forEach(System.out::println);
		System.out.println("...");
	}
	
	static void printSpacer()
	{
		System.out.println();
		System.out.println("========================================================");
		System.out.println();
	}
	
}
