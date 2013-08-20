<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td width="40%" valign="top">
				${ ui.includeFragment("kenyaemr", "patientSummary", [ patient: currentPatient ]) }
				${ ui.includeFragment("kenyaemr", "program/programHistories", [ patient: currentPatient, showClinicalData: false ]) }
			</td>

			<td width="60%" valign="top" style="padding-left: 5px">
				${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit, allowCheckIn: true, allowCheckOut: true ]) }

				<% if (activeVisit) { %>
				${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: activeVisit ]) }
				${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: activeVisit ]) }
				<% } %>
			</td>
		</tr>
	</table>
</div>

${ ui.includeFragment("kenyaemr", "showHtmlForm", [ id: "showHtmlForm", style: "display: none" ]) }
${ ui.includeFragment("kenyaemr", "dialogSupport") }