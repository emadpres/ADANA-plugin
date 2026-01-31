Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setDataAndType(TorrentUri, "application/x-bittorrent");
intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
startActivity(intent);;
