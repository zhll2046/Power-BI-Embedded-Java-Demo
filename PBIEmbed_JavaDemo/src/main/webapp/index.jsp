<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Power BI Embedded Demo</title>

<style type="text/css">
#embedContainer {
	width: 100%;
	height: 800px;
	overflow: hidden
}
</style>


</head>



<body>
	<h1>Power BI Embedded Demo</h1>
	<div>
		<form id="form1" action="embedReport" method="post">
			<table style="with: 50%">
				<tr>
					<td>Power BI Account</td>
					<td><input type="text" name="account" /></td>
				</tr>
				<tr>
					<td>Power BI Password</td>
					<td><input type="password" name="password" /></td>
				</tr>

				<tr>
					<td></td>
				</tr>

				<tr>
					<th colspan="4" align="left"><input type="radio"
						name="embedOption" value="dashboard">embed a dashboard </input> <input
						type="radio" name="embedOption" value="report">embed a
						report </input> <input type="radio" name="embedOption" value="tile">
						embed a tile </input></th>
				</tr>

				<tr>
					<td>GroupId:</td>
					<td><input type="text" id="groupId" name="groupId" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Row Level Security Parameters</td>
				</tr>
				<tr>
					<td>ReportId:</td>
					<td><input id="reportId" type="text" name="reportId" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Username:</td>
					<td><input type="text" name="user" /></td>
				</tr>
				</tr>
				<tr>
					<td>DashBoardId:</td>
					<td><input id="dashboardId" type="text" name="dashboardId" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Roles:</td>
					<td><input type="text" name="roles" /></td>
				</tr>
				</tr>
				<tr>
					<td>TileId:</td>
					<td><input id="tileId" type="text" name="tileId" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>DatasetId:</td>
					<td><input type="text" name="datasetId" /></td>
				</tr>
			</table>
			<input type="submit" value="Embed!" />
		</form>
		<input type="hidden" id="embedToken" value="" />
	</div>

	<script src="javascript/powerbi-2.6.5.js"></script>

	<script type="text/javascript">
		var embed = function() {

			// Read embed application token from textbox
			var embedToken = $("#embedToken").val();

			// Get embedType,report/dashboard/tile
			var embedType = $("input[type=radio][name=embedOption]:checked")
					.val();

			// Read embed URL from textbox
			var embedBaseUrl = "https://app.powerbi.com";

			// Get models. models contains enums that can be used.
			var models = window['powerbi-client'].models;

			// We give All permissions to demonstrate switching between View and Edit mode and saving report.
			var permissions = models.Permissions.All;

			// Get a reference to the embedded report HTML element
			var embedContainer = $('#embedContainer')[0];

			var embedGroupId = $("#groupId").val();

			switch (embedType) {
			//embed report
			case "report": {
				// Read report Id from textbox
				var EmbedReportId = $('#reportId').val();

				// a valid embedUrl looks the way like https://app.powerbi.com/reportEmbed?reportId=5ddbcb6f-8a19-4699-91af-199ec430ac43&groupId=3a5645b2-8d9d-4318-872a-113b5de18a74
				var EmbedUrl = embedBaseUrl + "/reportEmbed?reportId="
						+ EmbedReportId + "&groupId=" + embedGroupId;
				console.log(EmbedUrl);

				// Embed configuration used to describe the what and how to embed.
				// This object is used when calling powerbi.embed.
				// This also includes settings and options such as filters.
				// You can find more information at https://github.com/Microsoft/PowerBI-JavaScript/wiki/Embed-Configuration-Details.
				var config = {
					type : 'report',
					tokenType : models.TokenType.Embed,
					accessToken : embedToken,
					embedUrl : EmbedUrl,
					id : EmbedReportId,
					permissions : permissions,
					settings : {
						filterPaneEnabled : true,
						navContentPaneEnabled : true
					}
				};

				// Embed the report and display it within the div container.
				var report = powerbi.embed(embedContainer, config);

				// Report.off removes a given event handler if it exists.
				report.off("loaded");

				// Report.on will add an event handler which prints to Log window.
				report.on("loaded", function() {
					console.log("Loaded");
				});

				report.on("error", function(event) {
					console.log(event.detail);
					report.off("error");
				});

			}

			case "dashboard": {

				// Read report Id from textbox
				var EmbedDashboarId = $('#dashboardId').val();

				// a valid embedUrl looks the way like https://app.powerbi.com/reportEmbed?reportId=5ddbcb6f-8a19-4699-91af-199ec430ac43&groupId=3a5645b2-8d9d-4318-872a-113b5de18a74
				var EmbedUrl = embedBaseUrl + "/dashboardEmbed?dashboardId="
						+ EmbedDashboarId + "&groupId=" + embedGroupId;
				console.log(EmbedUrl);

				// Embed configuration used to describe the what and how to embed.
				// This object is used when calling powerbi.embed.
				// This also includes settings and options such as filters.
				// You can find more information at https://github.com/Microsoft/PowerBI-JavaScript/wiki/Embed-Configuration-Details.
				var config = {
					type : 'dashboard',
					tokenType : models.TokenType.Embed,
					accessToken : embedToken,
					embedUrl : EmbedUrl,
					id : EmbedDashboarId,
					permissions : permissions,
					settings : {
						filterPaneEnabled : true,
						navContentPaneEnabled : true
					}
				};

				// Embed the report and display it within the div container.
				var dashboard = powerbi.embed(embedContainer, config);

				// Report.off removes a given event handler if it exists.
				dashboard.off("loaded");

				// Report.on will add an event handler which prints to Log window.
				dashboard.on("loaded", function() {
					console.log("Loaded");
				});

				dashboard.on("error", function(event) {
					console.log(event.detail);
					report.off("error");
				});

			}
			case "tile": {

				// Read dashboard Id from textbox
				var EmbedDashboarId = $('#dashboardId').val();

				// Read tile Id from textbox
				var EmbedTileId = $('#tileId').val();

				// a valid embedUrl looks the way like https://app.powerbi.com/reportEmbed?reportId=5ddbcb6f-8a19-4699-91af-199ec430ac43&groupId=3a5645b2-8d9d-4318-872a-113b5de18a74
				var EmbedUrl = embedBaseUrl + "/embed?dashboardId="
						+ EmbedDashboarId + "&groupId=" + embedGroupId
						+ "&tileId=" + EmbedTileId;
				console.log(EmbedUrl);

				// Embed configuration used to describe the what and how to embed.
				// This object is used when calling powerbi.embed.
				// This also includes settings and options such as filters.
				// You can find more information at https://github.com/Microsoft/PowerBI-JavaScript/wiki/Embed-Configuration-Details.
				var config = {
					type : 'tile',
					tokenType : models.TokenType.Embed,
					accessToken : embedToken,
					embedUrl : EmbedUrl,
					id : EmbedTileId,
					dashboardId : EmbedDashboarId,
					permissions : permissions,
					settings : {
						filterPaneEnabled : true,
						navContentPaneEnabled : true
					}
				};

				// Embed the report and display it within the div container.
				var tile = powerbi.embed(embedContainer, config);

				// Report.off removes a given event handler if it exists.
				tile.off("loaded");

				// Report.on will add an event handler which prints to Log window.
				tile.on("loaded", function() {
					console.log("Loaded");
				});

				tile.on("error", function(event) {
					console.log(event.detail);
					report.off("error");
				});

			}
			}
		}
	</script>



	<script src="javascript/jquery-1.12.4.min.js"></script>
	<script type="text/javascript">
		$("#form1").submit(function(e) {
			var form = $(this);
			var url = form.attr('action');

			$.ajax({
				type : "POST",
				url : url,
				data : form.serialize(), // serializes the form's elements.
				success : function(data) {

					$("#embedToken").val(data);

					embed();
				}
			});

			e.preventDefault(); // avoid to execute the actual submit of the form.
		});

		$('input[type=radio][name=embedOption]').change(function() {
			if (this.value == 'report') {
				$("#reportId").attr("disabled", false);
				$("#dashboardId").attr("disabled", true);
				$("#tileId").attr("disabled", true);
			}

			else if (this.value == 'dashboard') {
				$("#dashboardId").attr("disabled", false);
				$("#reportId").attr("disabled", true);
				$("#tileId").attr("disabled", true);
			} else {
				$("#dashboardId").attr("disabled", false);
				$("#reportId").attr("disabled", true);
				$("#tileId").attr("disabled", false);
			}
		});

		$('input[type=radio]:first').attr('checked', 'checked').change();
	</script>

	<div id="embedContainer"></div>

</body>
</html>