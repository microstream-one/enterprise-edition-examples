package one.microstream.gigamap.examples;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import com.github.javafaker.Faker;

public class Person
{
	private final UUID      id         = UUID.randomUUID();
	private final String    firstName  ;
	private       String    lastName   ;
	private final LocalDate dateOfBirth;
	private       Address   address    ;
	
	public Person(final Faker faker)
	{
		this(
			faker.name().firstName(),
			faker.name().lastName(),
			LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()),
			new Address(faker)
		);
	}
	
	public Person(final String firstName, final String lastName, final LocalDate dateOfBirth, final Address address)
	{
		super();
		this.firstName   = firstName;
		this.lastName    = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address     = address;
	}
	
	public UUID getId()
	{
		return this.id;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth()
	{
		return this.dateOfBirth;
	}
	
	public Address getAddress()
	{
		return this.address;
	}
	
	public void setAddress(final Address address)
	{
		this.address = address;
	}

	@Override
	public String toString()
	{
		return "Person [id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", dateOfBirth="
			+ this.dateOfBirth + ", address=" + this.address + "]";
	}

}
