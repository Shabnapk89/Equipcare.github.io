$(document).ready(function(){
          $(document).on("click","#maintHistory",function(event) {
              $.ajax({url: "http://localhost:8080/hems/maintainHistory?equipmentId="+$(this).attr("value"),
                        success: function(result) {
                          $("#maintHistoryModal").html(result);
                          $('#maintHistoryModal').modal('show');
                }});
          });
    });