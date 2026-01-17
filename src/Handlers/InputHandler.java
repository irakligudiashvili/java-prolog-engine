package Handlers;

import Logic.Predicate;
import Logic.Rule;
import Terms.Atom;
import Terms.Term;
import Terms.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputHandler {
    private KnowledgeBase kb;
    private boolean hasQuery = false;

    public InputHandler(KnowledgeBase kb){
        this.kb = kb;
        this.start();
    }

    public void start(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\u001B[34mWelcome to \u001B[1;34mPROLOG \u001B[33m(type '!q' to exit)");

        while(true){
            System.out.print("\u001B[0m> ");
            String input = sc.nextLine().trim();

            if(input.equalsIgnoreCase("!q")){
                break;
            }

            if(input.isEmpty()){
                continue;
            }

            handleInput(input);
        }
    }

    private void handleInput(String input){
        input = input.replaceAll(" ", "");

        if(input.contains(":-")){
            Rule r = ruleParse(input);

            System.out.println(r.getHead() + " " + r.getBody());

            if(r != null){
                kb.addRule(r);
            }
        } else {
            Predicate p = predicateParse(input, false);

            if(p != null){
                if(hasQuery){
                    List<Map<Variable, Term>> answers = kb.query(p);
                    OutputHandler.printAnswer(answers);
                } else {
                    kb.addFact(p);
                }
            }
        }
    }

    private Rule ruleParse(String input){
        String[] parts = input.split(":-");
        if(parts.length != 2){
            OutputHandler.badSyntax();
            return null;
        }

        String head = parts[0];
        String body = parts[1];

        Predicate headPred = predicateParse(head, true);
        if(headPred == null){
            return null;
        }

        List<Predicate> bodyPreds = new ArrayList<>();

        List<String> preds = new ArrayList<>();

        StringBuilder current = new StringBuilder();
        int open = 0;

        for(int i = 0; i < body.length(); i++){
            char c = body.charAt(i);
            current.append(c);
            if(c == '('){
                open++;
            }
            if(c == ')'){
                open--;
            }

            if(c == ',' && open == 0){
                preds.add(current.substring(0, current.length() - 1));
                current.setLength(0);
            }
        }

        if(current.length() > 0){
            preds.add(current.toString());
        }

        for(String pred : preds){
            Predicate p = predicateParse(pred, true);

            if(p == null){
                return null;
            }

            bodyPreds.add(p);
        }

        return new Rule(headPred, bodyPreds);
    }

    private Predicate predicateParse(String input, boolean ruleMode){
        try{
            hasQuery = false;
            int i = 0;
            StringBuilder sb = new StringBuilder();

            if(!Character.isLetter(input.charAt(0))){
                OutputHandler.badSyntax();
                return null;
            }

            while(Character.isLetter(input.charAt(i))){
                sb.append(input.charAt(i));
                i++;
            }

            String name = sb.toString();

            if(input.charAt(i) != '('){
                OutputHandler.badSyntax();
                return null;
            }

            i++;
            List<String> args = new ArrayList<>();
            sb.setLength(0);

            while(true){
                int length = 0;

                while(Character.isLetter(input.charAt(i))){
                    sb.append(input.charAt(i));
                    i++;
                    length++;
                }

                String arg = sb.toString();
                sb.setLength(0);

                if(ruleMode){
                    if(!(length == 1 && Character.isUpperCase(arg.charAt(0)))){
                        return null;
                    }
                }

                if(!ruleMode && length == 1 && Character.isUpperCase(arg.charAt(0))){
                    if(hasQuery){
                        OutputHandler.badSyntax();
                        hasQuery = false;
                        return null;
                    }

                    hasQuery = true;
                }

                args.add(arg);

                if(input.charAt(i) == ','){
                    i++;
                    continue;
                }

                if(input.charAt(i) == ')'){
                    i++;
                    break;
                }

                OutputHandler.badSyntax();
                return null;
            }

            if(i != input.length()){
                OutputHandler.badSyntax();
                return null;
            }

            List<Term> terms = new ArrayList<>();
            for(String arg : args){
                if(arg.length() == 1 && Character.isUpperCase(arg.charAt(0))){
                    terms.add(new Variable(arg));
                } else {
                    terms.add(new Atom(arg));
                }
            }

            return new Predicate(name, terms);
        } catch (Exception e){
            OutputHandler.badSyntax();
        }

        return null;
    }
}
