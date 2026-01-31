QBUser user = new QBUser(); 
user.setId(53779);    
StringifyArrayList<String> tags = new StringifyArrayList();  
tags.add("man"); 
user.setTags(tags);   

QBUsers.updateUser(user, new QBEntityCallback<QBUser>(){
    @Override
    public void onSuccess(QBUser user, Bundle args) {

    }

    @Override
    public void onError(QBResponseException errors) {

    } 
});;
