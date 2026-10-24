// This class holds all the information regarding a character for the database

public class characterInfo
{
        // Setting up variables
        private int charID;
        private String charName;
        private double charHealth;
        private int charLevel;
        private double charExp;
        private boolean charInspo;

        public characterInfo(int charID, String charName, double charHealth, int charLevel, double charExp, boolean charInspo)
        {
            this.charID = charID;
            this.charName = charName;
            this.charHealth = charHealth;
            this.charLevel = charLevel;
            this.charExp = charExp;
            this.charInspo = charInspo;
        }

        // Getters
        public int getCharID() {return charID;}

        public String getCharName() {return charName;}

        public double getCharHealth() {return charHealth;}

        public int getCharLevel() {return charLevel;}

        public double getCharExp() {return charExp;}

        public boolean isCharInspo() {return charInspo;}

        // Setters

        public void setCharID(int charID) {this.charID = charID;}

        public void setCharName(String charName) {this.charName = charName;}

        public void setCharHealth(double charHealth) {this.charHealth = charHealth;}

        public void setCharLevel(int charLevel) {this.charLevel = charLevel;}

        public void setCharExp(double charExp) {this.charExp = charExp;}

        public void setCharInspo(boolean charInspo) {this.charInspo = charInspo;}

    }
