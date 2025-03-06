package one.microstream.gigamap.examples;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import one.microstream.gigamap.GigaMap;

public class RandomGenerator
{
	public static GigaMap<Person> createMap(final int size)
	{
		final GigaMap<Person> gigaMap = GigaMap.<Person>Builder()
			.withBitmapIdentityIndex(PersonIndices.id)
			.withBitmapIndex(PersonIndices.firstName)
			.withBitmapIndex(PersonIndices.lastName)
			.withBitmapIndex(PersonIndices.dateOfBirth)
			.withBitmapIndex(PersonIndices.city)
			.withBitmapIndex(PersonIndices.country)
			.build();
		
		final List<Person> data  = new ArrayList<>(size);
		final Faker        faker = new Faker();
		for(int i = 0; i < size; i++)
		{
			data.add(new Person(faker));
		}
		gigaMap.addAll(data);
		
		return gigaMap;
	}
}
