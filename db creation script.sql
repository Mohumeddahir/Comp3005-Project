create table users
    (user_id        varchar(6),
     name        varchar(15) not null,
     address        varchar(40) not null,
     phone_number   numeric(12, 0) not null,
     primary key (user_id)
    );
create table publisher
    (publisher_id            varchar(10), 
     name            varchar(20) not null, 
     address        varchar(40), 
     phone_number        numeric(12, 0), 
     email               varchar(20) not null,
     banking_acc        numeric(12, 0) not null, 
     primary key (publisher_id)
    );
	
create table book
    (book_id        varchar(6),
     publisher_id   varchar(6),
     author        varchar(15),
     title        varchar(20),
     number_of_pages        numeric(4,0) check (number_of_pages>0),
     price        numeric(4,0) check (price > 0),
     num_books        numeric(4,0) check(num_books >= 0),
     primary key (book_id),
     foreign key (publisher_id) references publisher
    );

create table orders
    (order_id        varchar(6), 
     quantity        numeric(4,0),
     locate        varchar(20),
     delivery_date        varchar(40),
     billing_address        varchar(40),
     primary key (order_id)
    
    );
	
create table owner
    (owner_id            varchar(5), 
     name        varchar(15),
     phone_number            numeric(12, 0), 
     primary key (owner_id)
        
    );


create table report
    (book_id            varchar(6), 
     owner_id            varchar(6), 
     sales        numeric(10, 2) check (sales >= 0), 
     expenditure    numeric(10, 2) check (expenditure >= 0), 
     genre            varchar(15), 
     author            varchar(15), 
     primary key (book_id),
     foreign key (book_id) references book,
     foreign key (owner_id) references owner
    );
	
create table track_order
    (order_id        varchar(8), 
     user_id        varchar(8),
     tracking_number        numeric(15, 0), 
    
     primary key (order_id),
     foreign key (order_id) references orders,
     foreign key (user_id) references users
    );
	
create table order_book
    (order_id        varchar(5), 
     book_id        varchar(8),
     primary key (order_id),
     foreign key (book_id) references book,
     foreign key (order_id) references orders
    );