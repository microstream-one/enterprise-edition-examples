package one.microstream.gigamap.examples;

import java.time.LocalDate;
import java.util.UUID;

import one.microstream.gigamap.Indexer;

/**
 * Static collection of indices for the {@link Person} entity.
 * <p>
 * Either implement Indexer by yourself or extend from one of the abstract inner types, like in this example.
 */
public class PersonIndices
{
	public final static Indexer.AbstractUUID<Person> id = new Indexer.AbstractUUID<>()
	{
		public String name()
		{
			return "id";
		}
		
		@Override
		protected UUID getUUID(final Person entity)
		{
			return entity.getId();
		}
	};

	public final static Indexer.AbstractString<Person> firstName = new Indexer.AbstractString<>()
	{
		public String name()
		{
			return "firstName";
		}
		
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getFirstName();
		}
	};
	
	public final static Indexer.AbstractString<Person> lastName = new Indexer.AbstractString<>()
	{
		public String name()
		{
			return "lastName";
		}
		
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getLastName();
		}
	};
	
	public final static Indexer.AbstractLocalDate<Person> dateOfBirth = new Indexer.AbstractLocalDate<>()
	{
		public String name()
		{
			return "dateOfBirth";
		}
		
		@Override
		protected LocalDate getDate(final Person entity)
		{
			return entity.getDateOfBirth();
		}
	};

	public final static Indexer.AbstractString<Person> city = new Indexer.AbstractString<>()
	{
		public String name()
		{
			return "city";
		}
		
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getAddress().getCity();
		}
	};

	public final static Indexer.AbstractString<Person> country = new Indexer.AbstractString<>()
	{
		public String name()
		{
			return "country";
		}
		
		@Override
		public String indexEntity(final Person entity)
		{
			return entity.getAddress().getCountry();
		}
	};
}
