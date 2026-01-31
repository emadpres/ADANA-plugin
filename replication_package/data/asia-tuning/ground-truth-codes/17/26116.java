public class ConstantData implements Parcelable {

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(project_title);....
        out.writeString(country);
        out.writeList(projectsList); // <<--
    }

    private ConstantData(Parcel in) {
        project_title = in.readString();....
        country = in.readString();
        projectsList = in.readList();;
