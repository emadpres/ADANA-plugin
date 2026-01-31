dingyueLL.setOnTouchListener(new OnTouchListener()
    {

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            setFlagTrue(Navigation.Dingyue.getPosition());
            setBtnLine();
            return false;
        }

    });

THEIMAGE.setOnTouchListener(new OnTouchListener()
    {

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {

            return false;
        }

    });;
