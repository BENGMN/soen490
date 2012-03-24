function deleteuser(userid, version) {
	$.ajax({
		type: "DELETE",
		url: "/MasterServer/controller",
		data: "command=deleteuser&userid="+ userid + "&responsetype=jsp&version=" + version,
	});
	alert("Helloworld");
}