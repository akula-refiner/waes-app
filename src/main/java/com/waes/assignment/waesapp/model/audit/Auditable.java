package com.waes.assignment.waesapp.model.audit;

public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);
}
