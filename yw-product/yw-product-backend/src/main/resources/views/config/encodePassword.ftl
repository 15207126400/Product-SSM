<@screen id="parameter" title="系统参数">
<div class="main-table">
    <div>
        <input id="password-origin" type="text"/>
    </div>
    <div id="password-en">
    </div>
</div>
<script>
    $('#password-origin').blur(function(){
        $.post('/sys/sysconfig/ajaxEncodePassword.json',{password:$('#password-origin').val()},function(data){
           $('#password-en').html(data.password);

        });
    });
</script>
</@screen>