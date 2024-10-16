# Clothing Shop
	 "Features of project"
      1. Ez to Manage Products
      2. Each Products can has difference size, color, price
	  3. Has Stock for manage Quantity of products with each information
	  4. Has Multiple Branchs
	  5. User can purchase Order before Order and can modify items
	  6. User Can import and export products to other branchs
      7. Has Expense Feature
	  8. Ez to view reports like daily,weekly,monthly

# Config H2 & POSTGRES
	# H2_Database
	datasource:
    	url: jdbc:h2:file:D:/others;DB_CLOSE_DELAY=-1;
    	username: lang
    	password: lang123
    	driverClassName: org.h2.Driver
	jpa:
		spring.jpa.database-platform: org.hibernate.dialect.H2Dialect
		properties:
			hibernate:
				enable_lazy_load_no_trans: true
		defer-datasource-initialization: true
		show-sql: true
		open-in-view: true
		hibernate:
			ddl-auto: create
	h2:
		console:
			enabled: true
			path: /h2-console
		settings:
			trace: false
			web-allow-others: false
	
	# DEPENDENCY runtimeOnly 'com.h2database:h2'
    ------------------------------------------------------------------------
    
    #POSTGRESQL
