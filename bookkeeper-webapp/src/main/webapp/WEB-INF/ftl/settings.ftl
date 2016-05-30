<#include "template.ftl" />

<#macro styles>
</#macro>

<#macro content>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            Настройки
        </h1>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#password-tab" data-toggle="tab" aria-expanded="true">
                                Пароль
                            </a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="password-tab">
                            <#if error??>
                                <div class="alert alert-danger alert-dismissible">
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                    <i class="icon fa fa-ban"></i> ${error}
                                </div>
                            </#if>
                            <#if check??>
                                <div class="alert alert-success alert-dismissible">
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                    <i class="icon fa fa-check"></i> ${check}
                                </div>
                            </#if>
                            <form id="changePassword" action="/password" method="post" class="form-horizontal">
                                <div class="form-group has-feedback">
                                    <label for="inputName" class="col-sm-2 control-label">Старый пароль</label>

                                    <div class="col-md-6">
                                        <input name="password" type="password" class="form-control" id="password">
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label for="inputName" class="col-sm-2 control-label">Новый пароль</label>

                                    <div class="col-md-6">
                                        <input name="newPassword" type="password" class="form-control" id="newPassword">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-danger">Сохранить</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- /.tab-pane -->
                    </div>
                    <!-- /.tab-content -->
                </div>
                <!-- /.nav-tabs-custom -->
            </div>
        </div>
    </section>

</div>
</#macro>

<#macro scripts>
<!-- jQuery Validation Plugin -->
<script src="/static/plugins/jquery-validation/jquery.validate.min.js"></script>

<script>
    $('#changePassword').validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                maxlength: 32
            },
            newPassword: {
                required: true,
                minlength: 6,
                maxlength: 32
            }
        },
        messages: {
            password: {
                required: 'Пароль не должен быть пустым',
                minlength: 'Пароль должен содержать минимум 6 символа',
                maxlength: 'Пароль должен содержать максимум 32 символов'
            },
            newPassword: {
                required: 'Новый пароль не должен быть пустым',
                minlength: 'Новый пароль должен содержать минимум 6 символа',
                maxlength: 'Новый пароль должен содержать максимум 32 символов'
            }
        },
        errorElement: 'label',
        errorPlacement: function(error, element) {
            error.insertBefore(element);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parent().addClass('has-error');
            return false;
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parent().removeClass('has-error');
            return false;
        }
    });

</script>

</#macro>
