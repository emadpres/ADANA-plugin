for(int i=0;i<num[position];i++)
    {
        holder.image = new ImageView(c);
        holder.image.setLayoutParams(new ViewGroup.LayoutParams(200,
                200));
        holder.image.setImageResource(R.mipmap.ic_launcher);
        //setting the onClickListener to image
        holder.image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //image clicked, do stuff
            }
        });
        ll.addView(holder.image);
    };
