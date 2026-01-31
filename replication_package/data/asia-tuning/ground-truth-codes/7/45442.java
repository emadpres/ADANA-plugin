@Override
public int getNumColumns() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        return super.getNumColumns();
    } else {
        try {
            Field numColumns = getClass().getSuperclass().getDeclaredField("mNumColumns");
            numColumns.setAccessible(true);
            return numColumns.getInt(this);
        } catch (Exception e) {
            return 1;
        }
    }
};
