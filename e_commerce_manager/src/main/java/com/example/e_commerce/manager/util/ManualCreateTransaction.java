package com.example.e_commerce.manager.util;

import org.springframework.transaction.support.DefaultTransactionDefinition;

public class ManualCreateTransaction {
    private static DefaultTransactionDefinition def;

    private ManualCreateTransaction(){}

    public static DefaultTransactionDefinition getManualCreateTransaction(){
        if (def == null){
            def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        }
        return def;
    }
}
