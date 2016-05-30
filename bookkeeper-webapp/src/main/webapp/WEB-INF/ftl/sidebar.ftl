<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/static/dist/img/boxed-bg.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${login}</p>
            </div>
        </div>

        <ul class="sidebar-menu">
            <li class="header">Меню</li>
            <li><a href="/home"><span> Главная</span></a></li>

            <li class="treeview active">
                <a href="#">
                    <i class="fa fa-pencil"></i>
                    <span>Добавить запись</span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/income"><i class="fa fa-plus-circle"></i> Доход</a></li>
                    <li><a href="/expenditure"><i class="fa fa-minus-circle"></i> Расход</a></li>
                </ul>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>