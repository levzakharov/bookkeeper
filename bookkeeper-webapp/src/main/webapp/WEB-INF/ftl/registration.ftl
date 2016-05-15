<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Bookkeeper | Registration Page</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition register-page">
<div class="register-box">
    <div class="register-logo">
        <a href="/"><b>Bookkeeper</b></a>
    </div>

    <div class="register-box-body">
        <p class="login-box-msg">Регистрация</p>

        <form id="registration" action="/registration" method="post">
            <div class="form-group has-feedback">
                <input id="login" name="login" type="text" class="form-control" placeholder="Логин">
            </div>
            <div class="form-group has-feedback">
                <input id="password" name="password" type="password" class="form-control" placeholder="Пароль">
            </div>
            <div class="form-group has-feedback">
                <input id="confirmPassword" name="confirmPassword" type="password" class="form-control" placeholder="Повторите пароль">
            </div>
            <div class="form-group">
                <label>Выберите пол</label>
                <select name="gender" class="form-control">
                    <option value="M">Мужской</option>
                    <option value="F">Женский</option>
                </select>
            </div>
            <div class="row">
                <!-- /.col -->
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">Регистрация</button>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <a href="/login" class="text-center">Я уже зарегистрирован</a>
    </div>
    <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 2.2.0 -->
<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- jQuery Validation Plugin -->
<script src="/static/plugins/jquery-validation/jquery.validate.min.js"></script>

<script>
    $('#registration').validate({
        rules: {
            name: {
                required: true,
                minlength: 3,
                maxlength: 16
            },
            login: {
                required: true,
                minlength: 3,
                maxlength: 16
            },
            password: {
                required: true,
                minlength: 6,
                maxlength: 32
            },
            confirmPassword: {
                required: true,
                equalTo: '#password'
            }
        },
        messages: {
            login: {
                required: 'Логин не должен быть пустым',
                minlength: 'Логин должен содержать минимум 3 символа',
                maxlength: 'Логин должен содержать максимум 16 символов',
            },
            password: {
                required: 'Пароль не должен быть пустым',
                minlength: 'Пароль должен содержать минимум 6 символа',
                maxlength: 'Пароль должен содержать максимум 32 символов'
            },
            confirmPassword: 'Введенные пароли не совпадают'
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
</script>

<script>
</script>
</body>
</html>
