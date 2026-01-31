Event=new String(edt.getText().toString());;
Event= edt.getText().toString();;
btn.setOnClickListener(new View.OnClickListener() 
{
    public void onClick(View v) 
    {
        Event = edt.getText().toString();
        if(Event.equals("Test"))
        {
           Toast.makeText(this, "Great!",Toast.LENGTH_LONG).show();
        }
    }
};
