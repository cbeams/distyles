!SLIDE subsection
# Spring Dependency Injection Styles
## _Choosing the right tool for the job_
<br><br>
## Chris Beams
### _Spring Framework committer_
### _Sr. Technical Staff, VMware_

!SLIDE subsection small bullets
# Welcome!
1. DI: a quick review<br><br>
2. explore Spring XML, annotation, and Java-based DI styles<br><br>
3. discuss pros and cons of each and mixing styles

!SLIDE bullets
* this presentation [http://cbeams.github.com/distyles](http://cbeams.github.com/distyles)
* recorded webinar [http://www.youtube.com/watch?v=dJh84cjMY3E](http://www.youtube.com/watch?v=dJh84cjMY3E)
* `style-*` project sources [http://github.com/cbeams/distyles](http://github.com/cbeams/distyles)

!SLIDE subsection
# Dependency Injection
## with a typical component: `TransferService`

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {




	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {

	    // TransferService has a dependency on 'accounts'


	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {

	    // where does the 'accounts' object come from?


	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {

	    AccountRepo accounts =        // direct instantiation?
	        new JdbcAccountRepo(...); // (not a great option)

	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {

	    AccountRepo accounts =        // singleton lookup?
	        AccountRepoFactory.get(); // (not a great option)

	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {

	    AccountRepo accounts =        // JNDI lookup?
	        jndiCtx.lookup("accts");  // (not a great option)

	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {







	  public void transfer(double amount,
	                       String fromId, String toId) {

	    // all of the above are forms of 'dependency lookup';
	    // transfer() becomes inflexible, can't be unit-tested

	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {

	  private final AccountRepo accounts;

	  public TransferService(AccountRepo accounts) {
	    this.accounts = accounts; // dependency injection!
	  }                           // (a much better option)

	  public void transfer(double amount,
	                       String fromId, String toId) {




	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE smaller
	@@@ java
	public class TransferService {

	  private final AccountRepo accounts;

	  public TransferService(AccountRepo accounts) {
	    this.accounts = accounts;
	  }

	  public void transfer(double amount,
	                       String fromId, String toId) {

	    // the transfer method remains simple, testable, etc


	    Account from = accounts.findById(fromId);
	    Account to = accounts.findById(fromId);

	    from.debit(amount);
	    to.credit(amount);
	  }
	}

!SLIDE small bullets
# DI really is that simple.
	@@@ java
	  public TransferService(AccountRepo accounts) {
	    this.accounts = accounts;
	  }

* use constructors and/or JavaBeans setters
* instead of "dependency lookup"
* dependency injection is just object-orientation _done right_

!SLIDE small
# which means...
## you don't need Spring (or any framework) to "do" DI

!SLIDE subsection
# demo: style-0-nospring

!SLIDE
# Roll-your-own DI
* no 3rd party framework dependency, but...
* leads to code duplication
* you'll end up creating your own framework of factories, etc in the end
* generally a waste of time

!SLIDE subsection
# Enter the Spring Container

!SLIDE
# The Spring Container
* "the only factory you'll ever need"
* allows you to configure DI using XML, annotations, or in pure Java
* goes far beyond simple DI with declarative tx management etc

!SLIDE smaller
# meaning DI code like this...
	@@@java
	public static void main(String... args)
	    throws InsufficientFundsException, IOException {

	  DataSource dataSource = ...; // not enough slide space!

	  Properties props = new Properties();
	  props.load(TransferScript.class.getClassLoader()
	        .getResourceAsStream("com/.../app.properties"));

	  TransferService transferService =
	    new DefaultTransferService(
	      new JdbcAccountRepository(dataSource),
	      new FlatFeePolicy(
	        Double.valueOf(props.getProperty("flatfee.amt"))));

	  transferService.setMinimumTransferAmount(
	      Double.valueOf(props.getProperty("min.xfer.amt")));

	  transferService.transfer(10.00, "A123", "C456");
	}

!SLIDE smaller
# becomes code like this.
	@@@java
	public static void main(String... args)

	  ApplicationContext ctx =
	    new GenericXmlApplicationContext("app-config.xml");

	  TransferService transferService =
	    ctx.getBean(TransferService.class);

	  transferService.transfer(10.00, "A123", "C456");
	}

!SLIDE subsection
# Style 1
## Spring `<beans>` XML

!SLIDE smaller
# Spring `<beans>` XML
	@@@java
	ApplicationContext ctx =
	  new GenericXmlApplicationContext("app-config.xml");
* available since Spring 1.0
* simple, general-purpose, flexible
* allows access to powerful features like tx management

!SLIDE subsection
# demo: style-1-xml

!SLIDE
# Spring `<beans>` XML
* allows for a centralized application 'blueprint'
* config can be changed without recompilation
* extremely well-understood in the industry

!SLIDE
# Spring `<beans>` XML
* but...
* can be verbose, not type-safe
* special tooling helps (STS, IDEA, NetBeans)

!SLIDE subsection
# Style 2
## Spring `<namespace:*>` XML

!SLIDE center
# e.g.
	@@@ xml
	<context:property-placeholder/>
	<jdbc:embedded-database/>
	<tx:annotation-driven/>
	<jee:jndi-lookup/>

!SLIDE
# Spring `<namespace:*>` XML
	@@@ xml
	<context:property-placeholder
	  locations="com/bank/app-config.xml/>
* available since Spring 2.0, more in 2.5, 3.0
* powerful _and_ concise
* again, not just about DI but also about enabling many features of the Spring container

!SLIDE subsection
# demo: style-2-namespace

!SLIDE
# Spring `<namespace:*>` XML
* reduces large amounts of XML boilerplate
* widely used in most Spring applications
* quite comprehensive support in Spring 3.1

!SLIDE small
# Spring `<namespace:*>` XML
	@@@xml
	<beans:*>
	<aop:*>
	<beans:*>
	<cache:*>
	<context:*>
	<jdbc:*>
	<jee:*>
	<jms:*>
	<lang:*>
	<mvc:*>
	<oxm:*>
	<task:*>
	<tool:*>
	<tx:*>
	<util:*>

!SLIDE
# Spring `<namespace:*>` XML
* works very well, but...
* application components must still be declared as `<bean>` elements

!SLIDE subsection
# Style 3
## Annotation-Driven Injection

!SLIDE center
# e.g.
	@@@
	@Autowired
	@Component
	<context:component-scan/>

!SLIDE
# Annotation-Driven Injection
	@@@xml
	<context:component-scan
	        base-package="com.bank"/>
* since Spring 2.5
* also includes the "TestContext" framework
* i.e. `@ContextConfiguration` and friends
* assisting with DI in JUnit tests

!SLIDE subsection
# demo: style-3-autowired

!SLIDE
# Annotation-Driven Injection
* _really_ concise, convenient
* has now become widely used
* especially for Spring MVC `@Controllers`

!SLIDE
# Annotation-Driven Injection
* a more _decentralized_ approach
* i.e. no single 'blueprint'
* still requires xml to bootstrap
* still requires xml for 3rd-party components

!SLIDE subsection
# Style 4
## Java-based Configuration

!SLIDE
# Java-based Configuration
* no XML required
* use `@Configuration` classes instead of `<beans>` XML documents
* use `@Bean` methods instead of `<bean>` elements

!SLIDE smaller
# bean definition
	@@@ java

	@Configuration
	@EnableTransactionManagement
	public class AppConfig {

		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = ...;

			return quoteService;
		}

	}

!SLIDE smaller
# bean definition
	@@@ java
	// @Configuration classes ~= <beans/> documents
	@Configuration
	@EnableTransactionManagement
	public class AppConfig {

		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = ...;

			return quoteService;
		}

	}

!SLIDE smaller
# bean definition
	@@@ java

	@Configuration
	@EnableTransactionManagement // ~= <tx:annotation-driven>
	public class AppConfig {

		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = ...;

			return quoteService;
		}

	}

!SLIDE smaller
# bean definition
	@@@ java

	@Configuration
	@EnableTransactionManagement
	public class AppConfig {
		// @Bean methods ~= <bean/> elements
		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = ...;

			return quoteService;
		}

	}

!SLIDE smaller
# bean definition
	@@@ java

	@Configuration
	@EnableTransactionManagement
	public class AppConfig {

		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = // instantiate

			return quoteService;
		}

	}

!SLIDE smaller
# bean definition
	@@@ java

	@Configuration
	@EnableTransactionManagement
	public class AppConfig {

		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = ...;
			// configure
			return quoteService;
		}

	}

!SLIDE smaller
# bean definition
	@@@ java

	@Configuration
	@EnableTransactionManagement
	public class AppConfig {

		@Bean
		public QuoteService quoteService() {
			RealTimeQuoteService quoteService = ...;

			return quoteService; // object managed by Spring
		}

	}

!SLIDE smaller
# bootstrap and use
	@@@ java

	public static void main(String... args) {

	  ApplicationContext ctx =
	    new AnnotationConfigApplicationContext(AppConfig.class);

	  QuoteService quoteService =
	    ctx.getBean(QuoteService.class);

	  System.out.println(quoteService.currentValue("AAPL"));
	}

!SLIDE smaller
# bootstrap and use
	@@@ java

	public static void main(String... args) {
	  // bootstrap the Spring container
	  ApplicationContext ctx =
	    new AnnotationConfigApplicationContext(AppConfig.class);

	  QuoteService quoteService =
	    ctx.getBean(QuoteService.class);

	  System.out.println(quoteService.currentValue("AAPL"));
	}

!SLIDE smaller
# bootstrap and use
	@@@ java

	public static void main(String... args) {

	  ApplicationContext ctx =
	    new AnnotationConfigApplicationContext(AppConfig.class);
	  // retrieve the bean we want to use in type-safe fashion
	  QuoteService quoteService =
	    ctx.getBean(QuoteService.class);

	  System.out.println(quoteService.currentValue("AAPL"));
	}

!SLIDE smaller
# bootstrap and use
	@@@ java

	public static void main(String... args) {

	  ApplicationContext ctx =
	    new AnnotationConfigApplicationContext(AppConfig.class);

	  QuoteService quoteService =
	    ctx.getBean(QuoteService.class);
	  // use the bean however desired
	  System.out.println(quoteService.currentValue("AAPL"));
	}

!SLIDE subsection
# demo: style-4-javaconfig

!SLIDE
# Java-Based Configuration
* type-safe, object-oriented
* can configure any component
* complete programmatic control

!SLIDE
# Java-Based Configuration
* no special tooling required
* support is now quite complete in Spring 3.1
* can mix-and-match with other styles seamlessly

!SLIDE subsection
# Style 5
## Hybrid Configuration

!SLIDE
# Hybrid Configuration
* choose from available styles to meet particular needs
* no one style is a silver bullet
* they're designed to work together!

!SLIDE subsection
# demo: style-5-hybrid

!SLIDE small
# Things to think about
* ease of use (`@Component`, `@Autowired`) vs. central blueprints (XML, `@Configuration` classes)
* there is no longer any excuse for verbose Spring XML
* in Spring 3.1, 100% XML-free configuration is an option
* remember, DI is just the tip of the iceberg in Spring!

!SLIDE subsection small bullets
# Thanks! Questions?
* [`@cbeams`](http://twitter.com/cbeams)
* [`@springframework`](http://twitter.com/springframework)
* this presentation: [http://cbeams.github.com/distyles](http://cbeams.github.com/distyles)
* `style-*` project sources: [http://github.com/cbeams/distyles](http://github.com/cbeams/distyles)
* [http://youtube.com/SpringSourceDev](http://youtube.com/SpringSourceDev)
* SpringSource blog: [http://blog.springsource.com](http://blog.springsource.com)
