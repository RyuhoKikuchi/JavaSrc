import java.util.Scanner;

public class DNAScore {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nucleotides = makeEqualLength(readinSequence(sc), readinSequence(sc));
        String nucl1 = nucleotides.substring(0, nucleotides.indexOf(';'));
        String nucl2 = nucleotides.substring(nucleotides.indexOf(';') + 1, nucleotides.length());
        System.out.printf("Comparing sequences:\n%s\n%s\nScore is: %d\n", nucl1, nucl2, scoreNucleotides(nucl1, nucl2));
        sc.close();
    }
    public static String readinSequence(Scanner input) {
        String seq = "";
        do {
            System.out.print("Enter a Sequence\n> ");
            seq = input.next().toUpperCase();
        } while (isValidSequences(seq));
        return seq;
    }
    public static boolean isValidSequences(String validSeq) {
        for (int i=0; i<validSeq.length(); i++) {
            char ch = validSeq.charAt(i);
            if (ch != 'A' && ch != 'C' && ch != 'G' && ch != 'T' && ch != '-') {
                System.out.println("Invalid sequences, Try Again");
                return true;
            }
        }
        return false;
    }
    public static String makeEqualLength(String seq1, String seq2) {
        int len1 = seq1.length();
        int len2 = seq2.length();
        if (len1 > len2) {
            for (int i=0; i<len1 - len2; i++) {
                seq2 += "-";
            }
        } else {
            for (int i=0; i<len2 - len1; i++) {
                seq1 += "-";
            }
        }
        return seq1.concat(";").concat(seq2);
    }
    public static int scoreNucleotides(String seq1, String seq2) {
        int score = 0;
        for (int i=0; i<seq1.length(); i++) {
            int pt = seq1.charAt(i) + seq2.charAt(i);
            if (pt < 130 && pt != 90) {
                score--;
            } else if (pt == 130 || pt == 134 || pt == 142 || pt == 168) {
                score++;
            } else if (pt != 90) {
                score -= 2;
            }
        }
        return score;
    }
}