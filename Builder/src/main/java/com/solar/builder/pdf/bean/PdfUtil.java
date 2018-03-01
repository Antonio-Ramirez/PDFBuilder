package com.solar.builder.pdf.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PdfUtil {
	
	private static final Log log = LogFactory.getLog(PdfUtil.class);
	
	public static void analizeForm(Form inputForm) throws Exception {
		inputForm.setName(inputForm.getInputFileName().split("\\.")[0]);
		
		PdfReader reader = null;
		try{
		    reader = new PdfReader(inputForm.getInputFileLocation() + inputForm.getInputFileName());
		    // Get the fields from the reader (read-only!!!)
		    AcroFields form = reader.getAcroFields();
		    // Loop over the fields and get info about them
			Set<String> fields = form.getFields().keySet();
		    List<String> sortedFields = new ArrayList<String>();
		    sortedFields.addAll(fields);
		    Collections.sort(sortedFields);
		    
		    for (String key : sortedFields) {
		    	com.solar.builder.pdf.bean.Field field = new com.solar.builder.pdf.bean.Field(key);
		    	
		    	List<FieldPosition> poss = form.getFieldPositions(key);
		    	for(FieldPosition pos : poss){
		    		
		    		Rectangle rect = new Rectangle(pos.position.getLeft()-(pos.position.getWidth()+10), pos.position.getTop(), pos.position.getLeft(), pos.position.getBottom());
		    		switch (form.getFieldType(key)) {
		    		case AcroFields.FIELD_TYPE_NONE:
		    			field.setType("None");
		    			break;
		    		case AcroFields.FIELD_TYPE_PUSHBUTTON:
		    			field.setType("PushButton");
		    			break;
		    		case AcroFields.FIELD_TYPE_CHECKBOX:
		    			rect = new Rectangle(pos.position.getRight(), pos.position.getTop(), pos.position.getRight()+pos.position.getWidth(), pos.position.getBottom());
		    			field.setType("CheckBox");
		    			field.setValue("On");
		    			break;
		    		case AcroFields.FIELD_TYPE_RADIOBUTTON:
		    			rect = new Rectangle(pos.position.getRight(), pos.position.getTop(), pos.position.getRight()+pos.position.getWidth(), pos.position.getBottom());
		    			field.setType("RadioButton");
		    			field.setValue("On");
		    			break;
		    		case AcroFields.FIELD_TYPE_TEXT:
		    			field.setType("TextField");
		    			break;
		    		case AcroFields.FIELD_TYPE_LIST:
		    			field.setType("List");
		    			break;
		    		case AcroFields.FIELD_TYPE_COMBO:
		    			field.setType("Combo");
		    			break;
		    		case AcroFields.FIELD_TYPE_SIGNATURE:
		    			field.setType("Signature");
		    			break;
		    		default:
		    			field.setType("Unknown");
		    		}
		    		
	        		RenderFilter filter = new RegionTextRenderFilter(rect);
	        		TextExtractionStrategy strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
	        		String alias = PdfTextExtractor.getTextFromPage(reader, pos.page, strategy);
	        		
	        		field.setAlias(StringUtils.isBlank(alias) ? key : StringUtils.trim(alias));
	        		field.setPage(pos.page);
	        		field.setTop(Math.round((800 - pos.position.getTop()) / 10));
	        		field.setRight(Math.round((pos.position.getLeft())/ 100));
		    	}
		    	
		    	inputForm.addField(field, field.getPage());
		    }
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(),e);
			throw e;
		} catch (IOException ioe) {
			log.error(ioe.getMessage(),ioe);
			throw ioe;
		} finally {
			try { 
				if (reader != null) {
					reader.close();
				}
			} catch (Exception ex) {
				log.error("Could not close resources");
				log.error(ex.getMessage(),ex);
			}
		}
	}
	
	public static String generatePdfFile(PdfBean pdf) throws Exception {
		String input = pdf.getInputFileLocation() + pdf.getInputFileName();
		String output = pdf.getOutputFileLocation() + pdf.getOutputFileName();
		InputStream is = null;
		PdfReader reader = null;
		FileOutputStream fos = null;
		
		try {
			reader = new PdfReader(input);
			
			Field field = reader.getClass().getDeclaredField("encrypted");
	        field.setAccessible(true);
	        field.set(reader, false); 
			
			fos = new FileOutputStream(output);
			PdfStamper stamp = new PdfStamper(reader, fos);
			PdfContentByte over = stamp.getOverContent(1);
			
			AcroFields form = stamp.getAcroFields();
			// fields
			Map<String, String> fields = pdf.getFields();
			for (String key : fields.keySet()) {
				log.debug(key + ": "+form.setField(key, fields.get(key)));
			}
			
			// table
			Map<String, PdfTable> tabs = pdf.getTablefields();
			for (String tabname : tabs.keySet()) {
				
				log.debug("Getting table:" + tabname);
				
				PdfTable tab = tabs.get(tabname);
				// create table properties
				PdfPTable table = null;
				if (tab.getHeaders().size() > 0) {
					table = new PdfPTable(tab.getHeaders().size());
					
					log.debug("Table has columns = " + tab.getHeaders().size());
					
				} else {
					table = new PdfPTable(tab.getCells().get(0).size());
					
					log.debug("Table has columns(cells) = " + tab.getCells().get(0).size());
					
				}
				// table widths
				float[] cellwidths = new float[tab.getHeaders().size()];
				int windex = 0;
				for (PdfTableHeader header : tab.getHeaders()) {
					cellwidths[windex] = header.getWidth();
					windex++;
				}
				if (log.isDebugEnabled()) { // avoids iteration if not in debug
											// mode
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < cellwidths.length; i++) {
						sb.append(cellwidths[i]);
						sb.append(",");
					}
					log.debug("Setting cell widths to " + sb.toString());
				}
				table.setWidths(cellwidths);
				switch (tab.getAlignment()) {
					case (AlignablePdfElement.ALIGN_LEFT):
						table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
						break;
					case (AlignablePdfElement.ALIGN_CENTER):
						table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
						break;
					case (AlignablePdfElement.ALIGN_RIGHT):
						table.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
						break;
				}
				// populate headers
				Font f = new Font(FontFamily.HELVETICA, 6);
				for (PdfTableHeader header : tab.getHeaders()) {
					PdfPCell cell = createNewCell(f, header.getValue(), 1);
					switch (header.getAlignment()) {
						case (AlignablePdfElement.ALIGN_LEFT):
							cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
							break;
						case (AlignablePdfElement.ALIGN_CENTER):
							cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							break;
						case (AlignablePdfElement.ALIGN_RIGHT):
							cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
							break;
					}
					table.addCell(cell);
				}
				// populate data
				Map<Integer, List<PdfTableCell>> cells = tab.getCells();
				for (Integer rowindex : cells.keySet()) {
					List<PdfTableCell> tcells = cells.get(rowindex);
					for (PdfTableCell tc : tcells) {
						
						log.debug("Adding new cell " + tc.toString());
						
						PdfPCell cell = createNewCell(f, tc.getValue(), 1);
						switch (tc.getAlignment()) {
							case (AlignablePdfElement.ALIGN_LEFT):
								cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
								break;
							case (AlignablePdfElement.ALIGN_CENTER):
								cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								break;
							case (AlignablePdfElement.ALIGN_RIGHT):
								cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
								break;
						}
						table.addCell(cell);
					}
				}
				table.setTotalWidth(tab.getWidth());
				table.writeSelectedRows(tab.getRowStart(), tab.getRowEnd(), tab.getXpos(), tab.getYpos(), over);
			}
			stamp.setFormFlattening(true);
			stamp.close();
			fos.flush();
			fos.close();
			return output;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception ex) {
				log.error("Could not close resources");
				log.error(ex.getStackTrace());
			}
		}
	}
	
	/**
	 * Creates a new PDF cell
	 * @param f
	 * @param content
	 * @param colspan
	 * @return
	 */
	private static PdfPCell createNewCell(Font f, String content, int colspan) {
		
		Paragraph p = new Paragraph();
		p.add(new Chunk(content, f));
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderColor(BaseColor.BLACK);
		cell.disableBorderSide(PdfPCell.LEFT);
		cell.disableBorderSide(PdfPCell.RIGHT);
		cell.disableBorderSide(PdfPCell.TOP);
		cell.disableBorderSide(PdfPCell.BOTTOM);
		cell.setColspan(colspan);
		cell.setPadding(0);
		return cell;
	}
}
