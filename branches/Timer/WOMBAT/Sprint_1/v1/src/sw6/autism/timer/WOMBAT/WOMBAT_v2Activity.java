package sw6.autism.timer.WOMBAT;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WOMBAT_v2Activity extends Activity {
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String[] items1 = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        	    "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        	    "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        	    "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
        	    "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
        	    "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
        	    "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
        	    "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
        	    "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
        	    "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
        	    "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
        	    "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
        	    "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
        	    "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
        	    "Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia"};
        String[] items2 = {"Bente", "Kurtine", "Olea"};
        
        ListView list1 = (ListView)findViewById(R.id.listView1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, items1);
        
        list1.setAdapter(adapter1);
        
        ListView list2 = (ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, items2);
        
        list2.setAdapter(adapter2);
    }
}