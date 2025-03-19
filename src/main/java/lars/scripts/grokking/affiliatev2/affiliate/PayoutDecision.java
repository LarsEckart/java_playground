package lars.scripts.grokking.affiliatev2.affiliate;

public sealed interface PayoutDecision permits DontSendPayout, SendPayout {}
