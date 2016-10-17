/**
 * Created by ling cao on 2016/10/8.
 */
$("#codeForm").submit(function(e) {

    var url = "/code";

    $.ajax({
        type: "POST",
        url: url,
        data: $("#codeForm").serialize(),
        success: function(data)
        {
            if (data.success) {
                $('#outputValue').val(data.output);
                $('#timeConsumed').val(data.timeConsumed);
            }
        }
    });

    e.preventDefault();
});
