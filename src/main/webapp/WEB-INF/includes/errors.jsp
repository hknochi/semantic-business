<form:errors path="*">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <form:errors path="*" cssClass="error" element="p" />
    </div>
</form:errors>
