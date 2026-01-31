/* Iterate File */
List<String> lst = new ArrayList<>();
String line = null;
while((line = br.readLine()) != null) {
    if(lst.size() == 100) {
        lst.remove(0);
    }
    lst.add(line);
}
br.close(); 

/* Make Text */
StringBuilder sb = new StringBuilder();
for(String s : lst) {
    sb.append(s).append("\n");
}
text = sb.toString();

/* Clear ArrayList */
lst.clear();;
