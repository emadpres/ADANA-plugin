LinearLayout lila = new LinearLayout(this);
        ArrayList<Button> alb = new ArrayList<Button>();
        int nButton = 10;
        for (int i = 0; i < nButton; i++)
        {
            alb.add(new Button(this));
            lila.addView(alb.get(i));
        }
        //works the same way with TextView
        alb.get(5).setText("myButton");;
