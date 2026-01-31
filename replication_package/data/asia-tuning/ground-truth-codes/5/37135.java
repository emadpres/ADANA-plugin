HttpPost post = new HttpPost(
         "https://www.placeyoururlhere.com");
         post.setHeader(HTTP.CONTENT_TYPE,"application/json" );
         List<NameValuePair> nameValuePairs = new
         ArrayList<NameValuePair>(1);
         nameValuePairs.add(new BasicNameValuePair("json", json));
         post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

         HttpClient client = new DefaultHttpClient();
         HttpResponse resp = client.execute(post);
         HttpEntity entity = resp.getEntity();

         response = EntityUtils.toString(entity);;
