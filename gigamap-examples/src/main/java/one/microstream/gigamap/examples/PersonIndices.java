package one.microstream.gigamap.examples;

import java.time.LocalDate;
import java.util.UUID;

import one.microstream.gigamap.IndexerLocalDate;
import one.microstream.gigamap.IndexerString;
import one.microstream.gigamap.IndexerUUID;

/**
 * Static collection of indices for the {@link Person} entity.
 * <p>
 * Either implement Indexer by yourself or extend from one of the abstract inner types, like in this example.
 */
public class PersonIndices
{
	public final static IndexerUUID<Person> id = new IndexerUUID.Abstract<>()
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

	public final static IndexerString<Person> firstName = new IndexerString.Abstract<>()
	{
		public String name()
		{
			return "firstName";
		}
		
		@Override
		public String getString(final Person entity)
		{
			return entity.getFirstName();
		}
	};
	
	public final static IndexerString<Person> lastName = new IndexerString.Abstract<>()
	{
		public String name()
		{
			return "lastName";
		}
		
		@Override
		public String getString(final Person entity)
		{
			return entity.getLastName();
		}
	};
	
	public final static IndexerLocalDate<Person> dateOfBirth = new IndexerLocalDate.Abstract<>()
	{
		public String name()
		{
			return "dateOfBirth";
		}
		
		@Override
		protected LocalDate getLocalDate(final Person entity)
		{
			return entity.getDateOfBirth();
		}
	};

	public final static IndexerString<Person> city = new IndexerString.Abstract<>()
	{
		public String name()
		{
			return "city";
		}
		
		@Override
		public String getString(final Person entity)
		{
			return entity.getAddress().getCity();
		}
	};

	public final static IndexerString<Person> country = new IndexerString.Abstract<>()
	{
		public String name()
		{
			return "country";
		}
		
		@Override
		public String getString(final Person entity)
		{
			return entity.getAddress().getCountry();
		}
	};
}
