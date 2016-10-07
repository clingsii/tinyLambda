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
            alert(data);
        }
    });

    e.preventDefault();
});
