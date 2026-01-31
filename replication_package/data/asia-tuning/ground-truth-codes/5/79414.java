long mId = getListView().getAdapter().getItemId(i);
Uri uri = Uri.parse(MyTodoContentProvider.CONTENT_URI+"/"+ mId);
getContentResolver().delete(uri, null, null);;
