/**
 * @author Pablo Johnson (pablo.88j@gmail.com)
 */
public class ExampleActivity extends AppCompatActivity implements TouchableWrapperListener {
  
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TouchableWrapper touchableMap = (TouchableWrapper) findViewById(R.id.touchableMap);
        touchableMap.setListener(this);
  }
   
  @Override
    public void onTouchStart() {
        //DO your stuff
    }

    @Override
    public void onTouchEnd() {
        //DO your stuff
    } 
  
}