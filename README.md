# Listable - Android Library

Simple, flexible, powerful. With this library you can easily list any entity in your application. 

Simply define the @ListableEntity Annotation in your POJO class, and @ListableField in the fields you want to display.


### Base Example

**Your POJO class:**
```
@ListableEntity
public class Product {


    @ListableField
    private String name;
    
    private String code;

    private float price;
    
    private int stock;
    
    //Getters and setters

}
```

**In your activity**
```
public class BaseExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Referencing views
        setContentView(R.layout.activity_with_recycler_view);//A simple activity with a RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //Configure RecyclerView
        ListableAdapter<Product> adapter = new ListableAdapter<>(Product.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //Adding data
        List<Product> yourData = getData();//Your collection obtained from Web Service, Database, etc
        adapter.add(yourData);
    }

    private List<Product> getData(){
        List<Product> list = new ArrayList<>();//
        list.add(new Product("Clean Code","9780132350884",29.99f, 30));
        list.add(new Product("The Pragmatic Programmer","9780201616224",34.99f, 50));
        return list;
    }
    
}

```

**Result:**

![Base example](https://drive.google.com/open?id=1-UunLbpfqxSejTofAzBSr42qF__kcJ4P/preview)

### Custom Item View Example

You can define your custom layout item like this:

**Create item layout file**

``` item_product.xml ```

```

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Name:"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/textViewName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="ISBN:"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/textViewIsbn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>

```

**Your POJO class:**
```
@ListableEntity(layoutResource = R.layout.item_product)
public class Product {


    @ListableField(viewResource = R.id.textViewName)
    private String name;

    @ListableField(viewResource = R.id.textViewIsbn)
    private String isbn;

    private float price;

    private int stock;
    
    //Getters and setters

}
```

**In your activity (Exactly the same of first example)**
```
public class CustomItemViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Referencing views
        setContentView(R.layout.activity_with_recycler_view);//A simple activity with a RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //Configure RecyclerView
        ListableAdapter<Product> adapter = new ListableAdapter<>(Product.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //Adding data
        List<Product> yourData = getData();//Your collection obtained from Web Service, Database, etc
        adapter.add(yourData);
    }

    private List<Product> getData(){
        List<Product> list = new ArrayList<>();//
        list.add(new Product("Clean Code","9780132350884",29.99f, 30));
        list.add(new Product("The Pragmatic Programmer","9780201616224",34.99f, 50));
        return list;
    }
    
}

```

Result:

![item_product.xml](https://drive.google.com/open?id=191mWpQjBSziuLWUxE65ndZzu8NGRipib/preview)

### Custom Formatter Example

You can specify how the value of a field is formatted. For example a float can be formatted as currency or percent.
You can do that in the following way:


**Create item layout file** 

``` item_product_with_price_and_stock.xml ```

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">


    <LinearLayout
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Name:"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/textViewName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="ISBN:"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/textViewIsbn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </LinearLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">


        <TextView
            android:id="@+id/textViewPrice"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:background="@android:color/holo_green_dark"
            android:padding="8dp"
            android:text="$0.00"
            android:textColor="@android:color/white"
            android:textSize="18sp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="Stock"
            android:textSize="12sp" />

        <TextView
            android:id="@+id/textViewStock"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="-" />

    </LinearLayout>


</LinearLayout>
```

![item_product_with_price_and_stock.xml](https://drive.google.com/open?id=1tm_5gCUS5jPYKip5D5SbBQmeeekc8XMQ/preview)


**Your POJO class:**
```
@ListableEntity(layoutResource = R.layout.item_product_with_price_and_stock)
public class Product {


    @ListableField(viewResource = R.id.textViewName)
    private String name;

    @ListableField(viewResource = R.id.textViewIsbn)
    private String isbn;

    @ListableField(viewResource = R.id.textViewPrice, formatter = CurrencyFormatter.class)
    private float price;

    @ListableField(viewResource = R.id.textViewStock)
    private int stock;
	
    //Getters and setters

}
```

**In your activity (Exactly the same of first example)**

```
public class CustomItemViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Referencing views
        setContentView(R.layout.activity_with_recycler_view);//A simple activity with a RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //Configure RecyclerView
        ListableAdapter<Product> adapter = new ListableAdapter<>(Product.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //Adding data
        List<Product> yourData = getData();//Your collection obtained from Web Service, Database, etc
        adapter.add(yourData);
    }

    private List<Product> getData(){
        List<Product> list = new ArrayList<>();//
        list.add(new Product("Clean Code","9780132350884",29.99f, 30));
        list.add(new Product("The Pragmatic Programmer","9780201616224",34.99f, 50));
        return list;
    }
    
}

```
Result:

![Custom view example](https://drive.google.com/open?id=1-WvStaD-mxB_YrMWCQ_GMI51mgPq-MWK/preview)

### Create your Formatter Example

You can define your custom formatters for Any type of View (TextView, ImageView, etc) 
and type of value (Float, Date, etc ) implemtenting the interface ListableFormatter.

//TODO
