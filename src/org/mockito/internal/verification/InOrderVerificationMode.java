/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.verification;

import java.util.Arrays;
import java.util.List;

import org.mockito.internal.invocation.AllInvocationsFinder;
import org.mockito.internal.invocation.Invocation;
import org.mockito.internal.invocation.InvocationMatcher;

public class InOrderVerificationMode extends VerificationModeImpl implements VerificationMode {

    protected InOrderVerificationMode(int wantedNumberOfInvocations, List<? extends Object> mocksToBeVerifiedInOrder,
            Verification verification) {
        super(wantedNumberOfInvocations, mocksToBeVerifiedInOrder, verification);
    }
    
    public void verify(List<Invocation> invocations, InvocationMatcher wanted) {
        invocations = new AllInvocationsFinder().getAllInvocations(this.getMocksToBeVerifiedInOrder());

        List<Verifier> verifiers = Arrays.asList(
                new MissingInvocationInOrderVerifier(),
                new NumberOfInvocationsInOrderVerifier());
        
        for (Verifier verifier : verifiers) {
            if (verifier.appliesTo(this)) {
                verifier.verify(invocations, wanted, this);
            }
        }
    }
}